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
}
