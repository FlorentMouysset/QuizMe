package quizme

import org.springframework.dao.DataIntegrityViolationException

import user.Etudiant;
import user.User

class AuthentificationController {

	static allowedMethods = [ index : "POST", save: "POST", update: "POST", delete: "POST"]


	def logout() {
		params.each{
			println "##" + it
		}
		println "déconnexion de " + params["id"]
		session.removeAttribute(params["id"])
		[params : params]
	}


	def identification(){
		def idUser = params["idField"]
		def mdpUser = params["textMdp"]

		def userObj = Authentification.identification(idUser, mdpUser)

		if(userObj != null){
			def verif = session.getAttribute(userObj.getId().toString())
			if(verif!= null){
				println "session existante pour cette utilisateur"
				params["cause"] = "Une session est déjà ouverte !"
				redirect(action: "errorIdent", params: params)
			}else{
				session.setAttribute(userObj.getId().toString(), userObj)
				assert userObj == session.getAttribute(userObj.getId().toString())
				println "authen reussie"
				params["userid"] = userObj.getId()
				redirect(controller: "room", action: "index", params: params)
			}
		}else{
			println "authen fail"
			params["cause"] = "Login ou mot de passe erroné"
			redirect(action: "errorIdent", params: params)
		}
	}

	def index() {
		//		println "ici index authen controller"
		//		println( params)
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
