package questions

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.hibernate.annotations.Cascade;
import org.springframework.dao.DataIntegrityViolationException

class QVraiFauxController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[QVraiFauxInstanceList: QVraiFaux.list(params), QVraiFauxInstanceTotal: QVraiFaux.count()]
	}

	def create() {
		println "QMC params : "+params
		session.removeAttribute("Question.id")
		[QVraiFauxInstance: new QVraiFaux(params)]
		println "QMC params after create : "+params
	}

	def save() {
		def QVraiFauxInstance = new QVraiFaux(params)
		if (!QVraiFauxInstance.save(flush: true)) {
			render(view: "create", model: [QVraiFauxInstance: QVraiFauxInstance])
			return
		}
		addToSession(QVraiFauxInstance)

		flash.message = message(code: 'default.created.message', args: [message(code: 'QVraiFaux.label', default: 'QVraiFaux'), QVraiFauxInstance.id])
		String action = session["session.origin"]
		println "TEST action : "+action
		if(action.equals("edit"))
			redirect(controller: "session", action: action, id: session["session.id"])
		else
			redirect(controller: "session", action: action)
	}

	def show(Long id) {
		def QVraiFauxInstance = QVraiFaux.get(id)
		if (!QVraiFauxInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'QVraiFaux.label', default: 'QVraiFaux'), id])
			redirect(action: "list")
			return
		}

		[QVraiFauxInstance: QVraiFauxInstance]
	}

	def edit(Long id) {
		def QVraiFauxInstance = QVraiFaux.get(id)
		if (!QVraiFauxInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'QVraiFaux.label', default: 'QVraiFaux'), id])
			redirect(action: "list")
			return
		}
		session["Question.id"] = id
		[QVraiFauxInstance: QVraiFauxInstance]
	}

	def update(Long id, Long version) {
		def QVraiFauxInstance = QVraiFaux.get(id)
		if (!QVraiFauxInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'QVraiFaux.label', default: 'QVraiFaux'), id])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (QVraiFauxInstance.version > version) {
				QVraiFauxInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'QVraiFaux.label', default: 'QVraiFaux')] as Object[],
						"Another user has updated this QVraiFaux while you were editing")
				render(view: "edit", model: [QVraiFauxInstance: QVraiFauxInstance])
				return
			}
		}

		QVraiFauxInstance.properties = params

		if (!QVraiFauxInstance.save(flush: true)) {
			render(view: "edit", model: [QVraiFauxInstance: QVraiFauxInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'QVraiFaux.label', default: 'QVraiFaux'), QVraiFauxInstance.id])
		String action = session["session.origin"]
		println "TEST action : "+action
		if(action.equals("edit"))
			redirect(controller: "session", action: action, id: session["session.id"])
		else
			redirect(controller: "session", action: action)
	}

	def delete(Long id) {
		println "delete here"
		def QVraiFauxInstance = QVraiFaux.get(id)
		if (!QVraiFauxInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'QVraiFaux.label', default: 'QVraiFaux'), id])
			redirect(action: "list")
			return
		}
		println "ca passe!"

		try {
			QVraiFauxInstance.delete(flush: true)
			println "test"
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'QVraiFaux.label', default: 'QVraiFaux'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			println "Erreur detectee"
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'QVraiFaux.label', default: 'QVraiFaux'), id])
			redirect(action: "show", id: id)
		}
	}

	//save la question + cree 2 reponses associees
	def save2() {
		println "QMC params save2 : "+params
		//Recuperer contenu du formulaire, pas ici dans le form ?
		println "question.id = "+session["Question.id"]

		def QVraiFauxInstance = new QVraiFaux(params)
		if (!QVraiFauxInstance.save(flush: true)) {
			render(view: "create", model: [QVraiFauxInstance: QVraiFauxInstance])
			return
		}

		params["id"] = QVraiFauxInstance.id

		println "params apres creationQuestionVF : "+params
		params["answer"] = "VRAI"
		ReponseController rc = new ReponseController()
		rc.saveVraiFaux()
		params["answer"] = "FAUX"
		rc.saveVraiFaux()

		session["newquestion"].each{
			println "C1 " + it
		}
		addToSession(QVraiFauxInstance)

		flash.message = message(code: 'default.created.message', args: [message(code: 'QVraiFaux.label', default: 'QVraiFaux'), QVraiFauxInstance.id])
		params.clear()
		params["idQuestion"] = QVraiFauxInstance.id
		String id = QVraiFauxInstance.id
//		redirect(action: "show", id: id)
		String action = session["session.origin"]
		println "TEST action : "+action
		if(action.equals("edit"))
			redirect(controller: "session", action: action, id: session["session.id"])
		else
			redirect(controller: "session", action: action)
	}

	def saveOrUpdate() {
		println "params saveOrUpdate : "+params
		println "myAction : "+params["myAction"]
		if(params["myAction"].equals("1")) {//add reponse -> save2
			println "GOTO save2"
			redirect(action: "save2", params:params)
		} else {//save or update here
			if(params["id"]==null) {
				println "GOTO save"
				save()
			}
			else {
				println "GOTO update"
				update(Long.parseLong(params["id"]), Long.parseLong(params["version"]))
			}
		}
	}

	def addToSession(def qInstance) {
		if(session["newquestion"] == null){
			session["newquestion"] = [qInstance.id]
		}else{
			session["newquestion"]  = session["newquestion"]  + qInstance.id
		}
		session["newquestion"].each{
			println "C2 " + it
		}
	}
}