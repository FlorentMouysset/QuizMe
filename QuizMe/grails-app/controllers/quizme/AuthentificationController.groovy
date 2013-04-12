package quizme

import org.springframework.dao.DataIntegrityViolationException

class AuthentificationController {

    static allowedMethods = [ index : "POST", save: "POST", update: "POST", delete: "POST"]

	def identification(){
		println "ici action identification"
		def idUser = params["idField"]
		def mdpUser = params["textMdp"]
		println idUser + " " + mdpUser
		
		if(false){//TODO verif idUser => ds bd -> mdp <--> User
			
		}else{
			redirect(action: "errorIdent", params: params)
		}
	}
	
    def index() {
		println "ici index authen controller"
		println( params)
        redirect(action: "list", params: params)
    }
	
	def errorIdent(){
		
	}

    def list(Integer max) {
		println "ici list authen controller"
        params.max = Math.min(max ?: 10, 100)
        [authentificationInstanceList: Authentification.list(params), authentificationInstanceTotal: Authentification.count()]
    }

    def create() {
        [authentificationInstance: new Authentification(params)]
    }

    def save() {
        def authentificationInstance = new Authentification(params)
        if (!authentificationInstance.save(flush: true)) {
            render(view: "create", model: [authentificationInstance: authentificationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'authentification.label', default: 'Authentification'), authentificationInstance.id])
        redirect(action: "show", id: authentificationInstance.id)
    }

    def show(Long id) {
        def authentificationInstance = Authentification.get(id)
        if (!authentificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'authentification.label', default: 'Authentification'), id])
            redirect(action: "list")
            return
        }

        [authentificationInstance: authentificationInstance]
    }

    def edit(Long id) {
        def authentificationInstance = Authentification.get(id)
        if (!authentificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'authentification.label', default: 'Authentification'), id])
            redirect(action: "list")
            return
        }

        [authentificationInstance: authentificationInstance]
    }

    def update(Long id, Long version) {
        def authentificationInstance = Authentification.get(id)
        if (!authentificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'authentification.label', default: 'Authentification'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (authentificationInstance.version > version) {
                authentificationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'authentification.label', default: 'Authentification')] as Object[],
                          "Another user has updated this Authentification while you were editing")
                render(view: "edit", model: [authentificationInstance: authentificationInstance])
                return
            }
        }

        authentificationInstance.properties = params

        if (!authentificationInstance.save(flush: true)) {
            render(view: "edit", model: [authentificationInstance: authentificationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'authentification.label', default: 'Authentification'), authentificationInstance.id])
        redirect(action: "show", id: authentificationInstance.id)
    }

    def delete(Long id) {
        def authentificationInstance = Authentification.get(id)
        if (!authentificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'authentification.label', default: 'Authentification'), id])
            redirect(action: "list")
            return
        }

        try {
            authentificationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'authentification.label', default: 'Authentification'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'authentification.label', default: 'Authentification'), id])
            redirect(action: "show", id: id)
        }
    }
}
