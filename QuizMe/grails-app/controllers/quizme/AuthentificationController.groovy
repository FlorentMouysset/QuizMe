package quizme

import org.springframework.dao.DataIntegrityViolationException

import user.Etudiant;
import user.User

class AuthentificationController {

	static allowedMethods = [ index : "POST", save: "POST", update: "POST", delete: "POST"]


	def logout() {
		//println "OLD déconnexion de " + params["id"]
		def userid = session["user"].getId().toString()
		println "déconnexion de " + userid
		
		//println "getServletContext()> "+ session.getServletContext() 
		
		servletContext.removeAttribute(userid)
		servletContext.removeAttribute(session.id)
		servletContext.removeAttribute(userid+"session")
		session.removeAttribute("user")
		[params : params]
	}


	def identification(){
		println "*********************************"
		println "*********************************"
		println "SS : " + request.getSession(true)
		println "SNEW : " + request.getSession().isNew()
		println "SID : " + request.getSession().getId()
		println "~ : " + request.getRequestedSessionId()
		println "V : " + session.getId()


		def idUser = params["idField"]
		def mdpUser = params["textMdp"]

		def userObj = Authentification.identification(idUser, mdpUser)

		if(userObj != null){//verif authentification ok
			def userid = userObj.getId().toString()
			if(servletContext[session.id] != null){
				println "dejà session d'un utilisateur!"
				params["cause"] = "Un utilisateur est déjà connecté !"
				redirect(action: "errorIdent", params: params)
			}else if(servletContext[userid] != null){
				println "compte déjà connecté !"
				def s = servletContext[userid+"session"]
				try{
					s.attributeNames
				}catch(IllegalStateException e){
					println "invalide"
					servletContext.removeAttribute(userid)
					servletContext.removeAttribute(servletContext[userid+"session"].id)
					servletContext.removeAttribute(userid+"session")
				}
				if(servletContext.getAttribute(userid+"session") == null){
					redirecListForum(userObj)
				}else{
					params["cause"] = "Votre compte est déjà ouvert !"
					redirect(action: "errorIdent", params: params)
				}
			}else{
				redirecListForum(userObj)
			}
		}else{
			println "authen fail"
			params["cause"] = "Login ou mot de passe erroné"
			redirect(action: "errorIdent", params: params)
		}
	}
	
	
	def redirecListForum(userObj){
		println "authen reussie " + userObj.getId() 
		def userid = userObj.getId().toString()
		session.setMaxInactiveInterval(1200)
		servletContext[userid] = userObj //on met l'utilisateur dans le context pour controler l'unicité de la connexion de cette utilisateur
		servletContext[userid+"session"]=session
		servletContext[session.id] = session.id //unicité de la session sur l'agent de l'utilisateur
		//session.setAttribute(userObj.getId().toString(), userObj)
		session.setAttribute("user", userObj )//attachement de l'utilisateur à la session
		redirect(controller: "room", action: "index", params: params)
	}

	def index() {
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
