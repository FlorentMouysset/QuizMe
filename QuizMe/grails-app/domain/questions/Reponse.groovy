package questions

class Reponse {
	
	String answer
	static belongsTo = [question:Question]

    static constraints = {
		answer(blank:false)
    }
}
