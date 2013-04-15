package quizme

import org.springframework.dao.DataIntegrityViolationException

class RoomController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		println "list room"
        params.max = Math.min(max ?: 10, 100)
		params.each{
			println "##" + it
		}
		println "!" + params["user"]
		println ">" + params["user"].class
//		println "!" + params["user"].getId()
		
        [roomInstanceList: Room.list(params), roomInstanceTotal: Room.count(), user:params["user"], userContextIsEtudiant : params["userContext"]]
    }

    def create() {
		println "create room"
		params.each{
			println "##" + it
		}
        [roomInstance: new Room(params)]
    }

    def save() {
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
