package quizme

import questions.QElaboration
import questions.Question
import questions.Reponse
import questions.Resultat

class Session {
	
	String nom
	SessionEtat etat
	//def mapResultats
//	static hasMany  = [mapResultats : Map, questions : Question ]
	static hasMany  = [ questions : Question ]
	static hasOne = [mapResultats: Map]
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
		mapResultats.each{
			def idQ = it.key
			def reponse = it.value
			if(reponse != ""){
				def question = QElaboration.findById(idQ)
//				println "--"  + idQ + " " + reponse + " " + question
				def rep = new Reponse(answer:reponse, question:question)
				question.addToReponses( rep )
			}
		}
	}

	boolean aParticipe(String userid){
		if(mapResultats!=null){
			mapResultats.containsKey(userid.toString())
		}else{
			false
		}
	}
		
	void ajoutParticipation(def userid, def param){
		def rep = [:]
		param.each{
			println "##" + it
		}
		questions.each {
			if (Question.isMultiChoix(it.getId()) ){
				String base = it.getId().toString() + "-"
				def repAux = [:]
				it.reponses.each{
					//					println "ici >" + base + it.getId()
					//					println "--" + params[base +  it.getId()]
					repAux[it.getId().toString()] =  param[base +  it.getId()] != null
				}
				rep[it.getId().toString()] = repAux
			}else{
				//				println "-> " + params[it.getId().toString()]
				rep[it.getId().toString()] =  param[it.getId().toString()] != null
			}
		}
		rep.each{
			println "key " + it.key
			println "value " + it.value
		}
		if(mapResultats == null){
			mapResultats = [:]
		}
		mapResultats.put(userid, rep)
		println "ici"
	}
	
}
