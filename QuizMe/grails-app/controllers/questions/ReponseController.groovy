package questions

import org.springframework.dao.DataIntegrityViolationException

class ReponseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reponseInstanceList: Reponse.list(params), reponseInstanceTotal: Reponse.count()]
    }

    def create() {
		println "ResponseCreate params : "+params
		if(params["QMultiChoix.id"]!=null){
			params["idQuestion"] = params["QMultiChoix.id"]
		}
		println "idQuestion : "+params["idQuestion"]
        [reponseInstance: new Reponse(params), idQuestion : params["idQuestion"] ]
    }

    def save() {
		println "saveReponse , params : "+params
		params["question.id"] = params['id']
		params["question"] = ["id":params["id"]]
		def reponseInstance = new Reponse(params)
		println "ici"
        if (!reponseInstance.save(flush: true)) {
            render(view: "create", model: [reponseInstance: reponseInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reponse.label', default: 'Reponse'), reponseInstance.id])

		def cont = reponseInstance.question.getClass().name.toString().replaceAll("questions.", "")

		redirect(controller: cont, action: "edit", id: params["question.id"])
    }

    def show(Long id) {
        def reponseInstance = Reponse.get(id)
        if (!reponseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reponse.label', default: 'Reponse'), id])
            redirect(action: "list")
            return
        }

        [reponseInstance: reponseInstance]
    }

    def edit(Long id) {
        def reponseInstance = Reponse.get(id)
        if (!reponseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reponse.label', default: 'Reponse'), id])
            redirect(action: "list")
            return
        }

        [reponseInstance: reponseInstance]
    }

    def update(Long id, Long version) {
        def reponseInstance = Reponse.get(id)
        if (!reponseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reponse.label', default: 'Reponse'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reponseInstance.version > version) {
                reponseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'reponse.label', default: 'Reponse')] as Object[],
                          "Another user has updated this Reponse while you were editing")
                render(view: "edit", model: [reponseInstance: reponseInstance])
                return
            }
        }

        reponseInstance.properties = params

        if (!reponseInstance.save(flush: true)) {
            render(view: "edit", model: [reponseInstance: reponseInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reponse.label', default: 'Reponse'), reponseInstance.id])
        redirect(action: "show", id: reponseInstance.id)
    }

    def delete(Long id) {
        def reponseInstance = Reponse.get(id)
        if (!reponseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reponse.label', default: 'Reponse'), id])
            redirect(action: "list")
            return
        }

        try {
            reponseInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reponse.label', default: 'Reponse'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reponse.label', default: 'Reponse'), id])
            redirect(action: "show", id: id)
        }
    }
	
	//reponse proposee par etudiant
	def propose(Long id) {
		println "propose ID recu : "+id
		println "ResponseCreate params : "+params
		params["idQuestion"] = id
		println "idQuestion : "+params["idQuestion"]
		[reponseInstance: new Reponse(params), idQuestion : params["idQuestion"] ]
	}
	
	def saveEtudiant() {
		println "saveEtudiantReponse , params : "+params
		params["question.id"] = params['id']
		params["question"] = ["id":params["id"]]
		//Modif answer
		def rep = params["answer"]
		params["answer"]="Proposée par etudiant : "+rep
		println "newAnswer : "+params["answer"]
		def reponseInstance = new Reponse(params)
		println "ici"
		if (!reponseInstance.save(flush: true)) {
			render(view: "create", model: [reponseInstance: reponseInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'reponse.label', default: 'Reponse'), reponseInstance.id])

		def cont = reponseInstance.question.getClass().name.toString().replaceAll("questions.", "")

		redirect(controller: cont, action: "show", id: params["question.id"])
	}
	
	def saveVraiFaux() {
		println "saveReponseVraiFaux , params : "+params
		params["question.id"] = params['id']
		params["question"] = ["id":params["id"]]
		def reponseInstance = new Reponse(params)
		println "ici saveVraiFaux"
		if (!reponseInstance.save(flush: true)) {
			render(view: "create", model: [reponseInstance: reponseInstance])
			return
		}
	}
	
}
