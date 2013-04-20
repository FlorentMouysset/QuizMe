package questions

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.hibernate.annotations.Cascade;
import org.springframework.dao.DataIntegrityViolationException

class QElaborationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [QElaborationInstanceList: QElaboration.list(params), QElaborationInstanceTotal: QElaboration.count()]
    }

    def create() {
		println "QMC params : "+params
		session.removeAttribute("Question.id")
        [QElaborationInstance: new QElaboration(params)]
		println "QMC params after create : "+params
    }

    def save() {
        def QElaborationInstance = new QElaboration(params)
        if (!QElaborationInstance.save(flush: true)) {
            render(view: "create", model: [QElaborationInstance: QElaborationInstance])
            return
        }

		addToSession(QElaborationInstance)
		
        flash.message = message(code: 'default.created.message', args: [message(code: 'QElaboration.label', default: 'QElaboration'), QElaborationInstance.id])
        //redirect(action: "show", id: QElaborationInstance.id)
		redirect(controller: "session", action: "create")
    }

    def show(Long id) {
        def QElaborationInstance = QElaboration.get(id)
        if (!QElaborationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'QElaboration.label', default: 'QElaboration'), id])
            redirect(action: "list")
            return
        }

        [QElaborationInstance: QElaborationInstance]
    }

    def edit(Long id) {
        def QElaborationInstance = QElaboration.get(id)
        if (!QElaborationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'QElaboration.label', default: 'QElaboration'), id])
            redirect(action: "list")
            return
        }
		session["Question.id"] = id
        [QElaborationInstance: QElaborationInstance]
    }

    def update(Long id, Long version) {
        def QElaborationInstance = QElaboration.get(id)
        if (!QElaborationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'QElaboration.label', default: 'QElaboration'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (QElaborationInstance.version > version) {
                QElaborationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'QElaboration.label', default: 'QElaboration')] as Object[],
                          "Another user has updated this QElaboration while you were editing")
                render(view: "edit", model: [QElaborationInstance: QElaborationInstance])
                return
            }
        }

        QElaborationInstance.properties = params

        if (!QElaborationInstance.save(flush: true)) {
            render(view: "edit", model: [QElaborationInstance: QElaborationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'QElaboration.label', default: 'QElaboration'), QElaborationInstance.id])
        //redirect(action: "show", id: QElaborationInstance.id)
		redirect(controller: "session", action: "create")
    }

    def delete(Long id) {
		println "delete here"
        def QElaborationInstance = QElaboration.get(id)
        if (!QElaborationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'QElaboration.label', default: 'QElaboration'), id])
            redirect(action: "list")
            return
        }
		println "ca passe!"

        try {
            QElaborationInstance.delete(flush: true)
			println "test"
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'QElaboration.label', default: 'QElaboration'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			println "Erreur detectee"
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'QElaboration.label', default: 'QElaboration'), id])
            redirect(action: "show", id: id)
        }
    }
	
	//save la question + rediriger vers add reponse
	def save2() {
		println "QMC params save2 : "+params
		//Recuperer contenu du formulaire, pas ici dans le form ?
		println "question.id = "+session["Question.id"]
		if(session["Question.id"]==null) {
			//premier passage creation new question
			def QElaborationInstance = new QElaboration(params)
			if (!QElaborationInstance.save(flush: true)) {
				render(view: "create", model: [QElaborationInstance: QElaborationInstance])
				return
			}

			session["newquestion"].each{
				println "C1 " + it
			}
			addToSession(QElaborationInstance)
			
			flash.message = message(code: 'default.created.message', args: [message(code: 'QElaboration.label', default: 'QElaboration'), QElaborationInstance.id])
			params.clear()
			params["idQuestion"] = QElaborationInstance.id
		} else {
			params.clear()
			params["idQuestion"] = session["Question.id"]
			session.removeAttribute("Question.id")
			println " remove question.id = "+session["Question.id"]
		}
		redirect(controller: "reponse", action: "create", params: params)
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
