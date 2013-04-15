package quizme

import user.Professeur

class Room {
	
	String nom
	String mdp
	Professeur admin
	static hasMany = [sessions:Session]
	
    static constraints = {
    }
}
