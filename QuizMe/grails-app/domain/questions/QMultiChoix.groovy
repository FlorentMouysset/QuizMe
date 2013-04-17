package questions

class QMultiChoix extends Question {
	
	static hasMany = [reponses:Reponse]
	
	static mapping = {
		reponses cascade: 'all'
	}

    static constraints = {
		//possibilité de verifier au moins 2 reponses entrees?
    }
}
