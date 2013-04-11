package quizme

import org.springframework.dao.DataIntegrityViolationException

class AuthentificationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "../index", params: params)
    }
}
