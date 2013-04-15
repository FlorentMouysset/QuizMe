package questions

import user.Professeur

class Question {
	String enonce
	QStatut statut
	static belongsTo = [professeur:Professeur]

    static constraints = {
		enonce(blank:false)
    }
}
