package quizme

import org.springframework.dao.DataIntegrityViolationException

import user.Etudiant

class RoomController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
		println "index room"
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		println "list room user : " + session["user"].getId()
        params.max = Math.min(max ?: 10, 100)

		def user = session.getAttribute("user") //récupération de l'utilisateur
		def userid = user.getId()
		def username = user.getNom()
		def contextUserType = user.class.equals( Etudiant.class )
        [roomInstanceList: Room.list(params), roomInstanceTotal: Room.count(), userContextIsEtudiant : contextUserType, username : username, userid : userid ]
    }

	def enter(){
		params.each{
			println "##" + it
		}
		def userId = session.getAttribute("user").getId()
		println "enter :  " + userId + " veut enter " + params["id"]
		
		def room = Room.findById(params["id"])//récupération de la room
		
		def rep;
		if(room.admin.id == userId){//si l'utilisateur est l'administrateur alors ouverture direct
			rep = true
		}else{
			rep = room.estParticipant(userId)//sinon l'utilisateur est-il un participant ?
		}
		println "E " + rep
		if(rep){//si ouverture ok
			session["room"] = room
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
			println "entre ds room + ajout participant " + session.getAttribute("user")  
			room.addParticipant(session.getAttribute("user"))
			//room.addParticipant(session.getAttribute("user").getId())
			session["room"] = room
			redirect(action: "inroom")
		}else{
			redirect(action: "identification", id : params["id"])
		}
	}
	
	def inroom(){
		println "inroom"
		[params:params]
	}
	
    def create() {
		println "create room user : " + session["user"].getId()
        [roomInstance: new Room(params), userid : session["user"].getId() ]
    }

    def save() {
		println "save room " + session["user"].getId()
		params["admin.id"] = session["user"].getId()
        params["admin"]=["id":session["user"].getId()]
		params.each{
				println "##" + it
		}
        def roomInstance = new Room(params)
        if (!roomInstance.save(flush: true)) {
            render(view: "create", model: [roomInstance: roomInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'room.label', default: 'Room'), roomInstance.id])
        redirect(action: "show", id: roomInstance.id)
    }

    def show(Long id) {
        def roomInstance = Room.get(id)
        if (!roomInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'room.label', default: 'Room'), id])
            redirect(action: "list")
            return
        }

        [roomInstance: roomInstance]
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
        redirect(action: "show", id: roomInstance.id)
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
            redirect(action: "show", id: id)
        }
    }
}
