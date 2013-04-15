package questions

class QMultiChoix extends Question {
	
	static hasMany = [reponses:Reponse]

    static constraints = {
		//possibilité de verifier au moins 2 reponses entrees?
    }
}
