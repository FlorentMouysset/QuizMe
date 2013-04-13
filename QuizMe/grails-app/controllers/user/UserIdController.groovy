package user

import org.springframework.dao.DataIntegrityViolationException

class UserIdController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userIdInstanceList: UserId.list(params), userIdInstanceTotal: UserId.count()]
    }

    def create() {
        [userIdInstance: new UserId(params)]
    }

    def save() {
        def userIdInstance = new UserId(params)
        if (!userIdInstance.save(flush: true)) {
            render(view: "create", model: [userIdInstance: userIdInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'userId.label', default: 'UserId'), userIdInstance.id])
        redirect(action: "show", id: userIdInstance.id)
    }

    def show(Long id) {
        def userIdInstance = UserId.get(id)
        if (!userIdInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userId.label', default: 'UserId'), id])
            redirect(action: "list")
            return
        }

        [userIdInstance: userIdInstance]
    }

    def edit(Long id) {
        def userIdInstance = UserId.get(id)
        if (!userIdInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userId.label', default: 'UserId'), id])
            redirect(action: "list")
            return
        }

        [userIdInstance: userIdInstance]
    }

    def update(Long id, Long version) {
        def userIdInstance = UserId.get(id)
        if (!userIdInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userId.label', default: 'UserId'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userIdInstance.version > version) {
                userIdInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'userId.label', default: 'UserId')] as Object[],
                          "Another user has updated this UserId while you were editing")
                render(view: "edit", model: [userIdInstance: userIdInstance])
                return
            }
        }

        userIdInstance.properties = params

        if (!userIdInstance.save(flush: true)) {
            render(view: "edit", model: [userIdInstance: userIdInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'userId.label', default: 'UserId'), userIdInstance.id])
        redirect(action: "show", id: userIdInstance.id)
    }

    def delete(Long id) {
        def userIdInstance = UserId.get(id)
        if (!userIdInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userId.label', default: 'UserId'), id])
            redirect(action: "list")
            return
        }

        try {
            userIdInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'userId.label', default: 'UserId'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'userId.label', default: 'UserId'), id])
            redirect(action: "show", id: id)
        }
    }
}
