package questions

class Question {
	String enonce
	QStatut statut

    static constraints = {
		enonce(blank:false)
    }
}
