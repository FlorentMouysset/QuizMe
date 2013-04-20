package questions

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.hibernate.annotations.Cascade;
import org.springframework.dao.DataIntegrityViolationException

class QLibreController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[QLibreInstanceList: QLibre.list(params), QLibreInstanceTotal: QLibre.count()]
	}

	def create() {
		println "QMC params : "+params
		session.removeAttribute("Question.id")
		[QLibreInstance: new QLibre(params)]
		println "QMC params after create : "+params
	}

	def save() {
		def QLibreInstance = new QLibre(params)
		if (!QLibreInstance.save(flush: true)) {
			render(view: "create", model: [QLibreInstance: QLibreInstance])
			return
		}
		addToSession(QLibreInstance)

		flash.message = message(code: 'default.created.message', args: [message(code: 'QLibre.label', default: 'QLibre'), QLibreInstance.id])
		String action = session["session.origin"]
		println "TEST action : "+action
		if(action.equals("edit"))
			redirect(controller: "session", action: action, id: session["session.id"])
		else
			redirect(controller: "session", action: action)
	}

	def show(Long id) {
		def QLibreInstance = QLibre.get(id)
		if (!QLibreInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'QLibre.label', default: 'QLibre'), id])
			redirect(action: "list")
			return
		}

		[QLibreInstance: QLibreInstance]
	}

	def edit(Long id) {
		def QLibreInstance = QLibre.get(id)
		if (!QLibreInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'QLibre.label', default: 'QLibre'), id])
			redirect(action: "list")
			return
		}
		session["Question.id"] = id
		[QLibreInstance: QLibreInstance]
	}

	def update(Long id, Long version) {
		def QLibreInstance = QLibre.get(id)
		if (!QLibreInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'QLibre.label', default: 'QLibre'), id])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (QLibreInstance.version > version) {
				QLibreInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'QLibre.label', default: 'QLibre')] as Object[],
						"Another user has updated this QLibre while you were editing")
				render(view: "edit", model: [QLibreInstance: QLibreInstance])
				return
			}
		}

		QLibreInstance.properties = params

		if (!QLibreInstance.save(flush: true)) {
			render(view: "edit", model: [QLibreInstance: QLibreInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'QLibre.label', default: 'QLibre'), QLibreInstance.id])
		String action = session["session.origin"]
		println "TEST action : "+action
		if(action.equals("edit"))
			redirect(controller: "session", action: action, id: session["session.id"])
		else
			redirect(controller: "session", action: action)
	}

	def delete(Long id) {
		println "delete here"
		def QLibreInstance = QLibre.get(id)
		if (!QLibreInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'QLibre.label', default: 'QLibre'), id])
			redirect(action: "list")
			return
		}
		println "ca passe!"

		try {
			QLibreInstance.delete(flush: true)
			println "test"
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'QLibre.label', default: 'QLibre'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			println "Erreur detectee"
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'QLibre.label', default: 'QLibre'), id])
			redirect(action: "show", id: id)
		}
	}

	//save la question + cree 2 reponses associees
	def save2() {
		println "QMC params save2 : "+params
		//Recuperer contenu du formulaire, pas ici dans le form ?
		println "question.id = "+session["Question.id"]

		def QLibreInstance = new QLibre(params)
		if (!QLibreInstance.save(flush: true)) {
			render(view: "create", model: [QLibreInstance: QLibreInstance])
			return
		}

		params["id"] = QLibreInstance.id

		session["newquestion"].each{
			println "C1 " + it
		}
		addToSession(QLibreInstance)

		flash.message = message(code: 'default.created.message', args: [message(code: 'QLibre.label', default: 'QLibre'), QLibreInstance.id])
		params.clear()
		params["idQuestion"] = QLibreInstance.id
		String id = QLibreInstance.id
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
