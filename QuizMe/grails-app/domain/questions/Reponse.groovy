package questions

class Reponse {
	
	String answer
	static belongsTo = [question:Question]
	String commentaire

    static constraints = {
		answer(blank:false)
		commentaire(nullable: true, blank: true)
    }
}
