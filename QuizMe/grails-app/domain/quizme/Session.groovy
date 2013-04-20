package quizme

import questions.Question

class Session {
	
	String nom
//	Room room
	SessionEtat etat
	static hasMany  = [questions : Question ]
	
	
    static constraints = {
//		room(nullable:true)
    }
	
	void addQuestions(List questionIdList){
		questionIdList.each {
			addToQuestions( Question.get(it) )
		}
	}
	
	void evolutionEtat(){
		println "evolutionEtat "
		if(etat == SessionEtat.CREATION || etat == SessionEtat.REFORMULATION){
			etat = SessionEtat.EN_COURS
		}else if(etat == SessionEtat.ELABORATION){
			etat = SessionEtat.REFORMULATION
		}else{
			etat = SessionEtat.FIN
		}
	}
	
	void evolutionElaboration(){
		etat = SessionEtat.ELABORATION
	}
}
