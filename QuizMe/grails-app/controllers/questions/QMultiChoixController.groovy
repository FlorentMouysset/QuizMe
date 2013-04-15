package questions

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
        [QMultiChoixInstance: new QMultiChoix(params)]
    }

    def save() {
        def QMultiChoixInstance = new QMultiChoix(params)
        if (!QMultiChoixInstance.save(flush: true)) {
            render(view: "create", model: [QMultiChoixInstance: QMultiChoixInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), QMultiChoixInstance.id])
        redirect(action: "show", id: QMultiChoixInstance.id)
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
        redirect(action: "show", id: QMultiChoixInstance.id)
    }

    def delete(Long id) {
        def QMultiChoixInstance = QMultiChoix.get(id)
        if (!QMultiChoixInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), id])
            redirect(action: "list")
            return
        }

        try {
            QMultiChoixInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'QMultiChoix.label', default: 'QMultiChoix'), id])
            redirect(action: "show", id: id)
        }
    }
}
