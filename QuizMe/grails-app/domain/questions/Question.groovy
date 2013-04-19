package questions

import user.Professeur

class Question {
	String enonce
	//QStatut statut

    static constraints = {
		enonce(blank:false)
    }
}
