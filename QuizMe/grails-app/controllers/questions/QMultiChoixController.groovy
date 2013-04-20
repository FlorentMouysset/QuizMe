package questions

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.hibernate.annotations.Cascade;
import org.springframework.dao.DataIntegrityViolationException

class QMultiChoixController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [QMultiChoixInstanceList: QMultiChoix.list(params), QMultiChoixInstanceTotal: QMultiChoix.count()]
    }

    def create() {
		println "QMC params : "+params
		session.removeAttribute("Question.id")
        [QMultiChoixInstance: new QMultiChoix(params)]
		println "QMC params after create : "+params
    }

    def save() {
        def QMultiChoixInstance = new QMultiChoix(params)
        if (!QMultiChoixInstance.save(flush: true)) {
            render(view: "create", model: [QMultiChoixInstance: QMultiChoixInstance])
            return
        }
//		if(session["newquestion"] == null){
//			session["newquestion"] = [QMultiChoixInstance.id]
//		}else{
//			session["newquestion"]  = session["newquestion"]  + QMultiChoixInstance.id
//		}
//					session["newquestion"].each{
//			println "C2 " + it
//		}
		
        flash.message = message(code: 'default.created.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), QMultiChoixInstance.id])
        //redirect(action: "show", id: QMultiChoixInstance.id)
		String action = session["session.origin"]
		println "TEST action : "+action
		
		addToSession(QMultiChoixInstance)
		
		if(action.equals("edit"))
			redirect(controller: "session", action: action, id: session["session.id"])
		else
			redirect(controller: "session", action: action)
	}

    def show(Long id) {
        def QMultiChoixInstance = QMultiChoix.get(id)
        if (!QMultiChoixInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), id])
            redirect(action: "list")
            return
        }

        [QMultiChoixInstance: QMultiChoixInstance]
    }

    def edit(Long id) {
        def QMultiChoixInstance = QMultiChoix.get(id)
        if (!QMultiChoixInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), id])
            redirect(action: "list")
            return
        }
		session["Question.id"] = id
        [QMultiChoixInstance: QMultiChoixInstance]
    }

    def update(Long id, Long version) {
        def QMultiChoixInstance = QMultiChoix.get(id)
        if (!QMultiChoixInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (QMultiChoixInstance.version > version) {
                QMultiChoixInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'QMultiChoix.label', default: 'QMultiChoix')] as Object[],
                          "Another user has updated this QMultiChoix while you were editing")
                render(view: "edit", model: [QMultiChoixInstance: QMultiChoixInstance])
                return
            }
        }

        QMultiChoixInstance.properties = params

        if (!QMultiChoixInstance.save(flush: true)) {
            render(view: "edit", model: [QMultiChoixInstance: QMultiChoixInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), QMultiChoixInstance.id])
        //redirect(action: "show", id: QMultiChoixInstance.id)
		redirect(controller: "session", action: "create")
    }

    def delete(Long id) {
		println "delete here"
        def QMultiChoixInstance = QMultiChoix.get(id)
        if (!QMultiChoixInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), id])
            redirect(action: "list")
            return
        }
		println "ca passe!"

        try {
            QMultiChoixInstance.delete(flush: true)
			println "test"
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			println "Erreur detectee"
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), id])
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
			def QMultiChoixInstance = new QMultiChoix(params)
			if (!QMultiChoixInstance.save(flush: true)) {
				render(view: "create", model: [QMultiChoixInstance: QMultiChoixInstance])
				return
			}

			session["newquestion"].each{
				println "C1 " + it
			}
			addToSession(QMultiChoixInstance)
			
			flash.message = message(code: 'default.created.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), QMultiChoixInstance.id])
			params.clear()
			params["idQuestion"] = QMultiChoixInstance.id
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
