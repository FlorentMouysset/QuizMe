package quizme

import org.springframework.dao.DataIntegrityViolationException

import user.Etudiant
import user.User

class RoomController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
		println "index room"
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		println "list room user : " + session["user.id"]
        params.max = Math.min(max ?: 10, 100)

		def userid = session.getAttribute("user.id") //récupération de l'utilisateur
		def user = User.findById(userid)
		def username = user.getNom()
		def contextUserType = Etudiant.estEtudiant(user)
        [roomInstanceList: Room.list(params), roomInstanceTotal: Room.count(), userContextIsEtudiant : contextUserType, username : username, userid : userid ]
    }

	def enter(){
		String userId = session.getAttribute("user.id") //récupération de l'utilisateur
		println "enter :  " + userId + " veut enter " + params["id"]
		def room = Room.findById(params["id"])//récupération de la room
		boolean rep;
		if(room.admin.id.toString() == userId){//si l'utilisateur est l'administrateur alors ouverture direct
			rep = true
		}else{
			rep = room.estParticipant(userId)//sinon l'utilisateur est-il un participant ?
		}
		if(rep){//si ouverture ok
			session["room.id"] = room.id
			redirect(action: "inroom")
		}else{//sinon authentification requise
			redirect(action: "identification", id : params["id"])
		}
	}
	
	def identification(){
		[params:params, roomId : params["id"]]
	}
	
	def enterRequest(){
		println "enterRequest"
		def mdpRoom = params["textMdp"]
		def room = Room.findById(params["id"])
		if(room.mdp == mdpRoom){
			//println "entre ds room + ajout participant " + session.getAttribute("user")  
			def userid = session.getAttribute("user.id") //récupération de l'utilisateur
			def user = User.findById(userid)
			room.addParticipant(user)
			session["room.id"] = room.id
			redirect(action: "inroom")
		}else{
			redirect(action: "identification", id : params["id"])
		}
	}
	
	def inroom(){
		println "inroom"
		session["newquestion"] = null
		String userId = session["user.id"]
		String roomId = session["room.id"]
		def user = User.findById(userId)
		def contextUserType = Etudiant.estEtudiant(user)
		def room = Room.findById(roomId)
		[roomInstance: Room.findById(roomId), userContextIsEtudiant : contextUserType]
	}
	
    def create() {
		println "create room user : " + session["user.id"]
        [roomInstance: new Room(params), userid : session["user.id"] ]
    }

    def save() {
		println "save room " + session["user.id"]
		params["admin.id"] = session["user.id"]
        params["admin"]=["id":session["user.id"]]
	/*	params.each{
				println "##" + it
		}*/
        def roomInstance = new Room(params)
        if (!roomInstance.save(flush: true)) {
            render(view: "create", model: [roomInstance: roomInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'room.label', default: 'Room'), roomInstance.id])
        redirect(action: "list")
    }

    def edit(Long id) {
        def roomInstance = Room.get(id)
        if (!roomInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'room.label', default: 'Room'), id])
            redirect(action: "list")
            return
        }

        [roomInstance: roomInstance]
    }

    def update(Long id, Long version) {
        def roomInstance = Room.get(id)
        if (!roomInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'room.label', default: 'Room'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (roomInstance.version > version) {
                roomInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'room.label', default: 'Room')] as Object[],
                          "Another user has updated this Room while you were editing")
                render(view: "edit", model: [roomInstance: roomInstance])
                return
            }
        }

        roomInstance.properties = params

        if (!roomInstance.save(flush: true)) {
            render(view: "edit", model: [roomInstance: roomInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'room.label', default: 'Room'), roomInstance.id])
        redirect(action: "list")
    }

    def delete(Long id) {
        def roomInstance = Room.get(id)
        if (!roomInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'room.label', default: 'Room'), id])
            redirect(action: "list")
            return
        }

        try {
            roomInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'room.label', default: 'Room'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'room.label', default: 'Room'), id])
            redirect(action: "list", id: id)
        }
    }
}
