package questions

class QLibre extends Question {
	
	static hasMany = [reponses:Reponse]
	
	static mapping = {
		reponses cascade: 'all'
	}

    static constraints = {
    }
}
