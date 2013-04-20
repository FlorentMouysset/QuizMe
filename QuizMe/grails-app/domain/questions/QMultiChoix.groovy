package questions

class QMultiChoix extends Question {
	
	static hasMany = [reponses:Reponse]
	
	static mapping = {
		reponses cascade: 'all'
	}

    static constraints = {
    }
}
