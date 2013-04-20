package quizme

import questions.QElaboration
import questions.Question
import questions.Reponse

class Session {
	
	String nom
	SessionEtat etat
	static hasMany  = [questions : Question ]	
	static mapping = {
		questions cascade: 'all'
	}
	
	
    static constraints = {
		nom(blank:false)
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

	void elaborationAjoutProposition(Map map){
		map.each{
			def idQ = it.key
			def reponse = it.value
			if(reponse != ""){
				def question = QElaboration.findById(idQ)
				println "--"  + idQ + " " + reponse + " " + question
				def rep = new Reponse(answer:reponse, question:question)
				question.addToReponses( rep )
			}
		}
	}
	
}
