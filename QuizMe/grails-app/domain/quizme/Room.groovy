package quizme

import user.Professeur

class Room {
	
	String nom
	String mdp
	Professeur admin
	static hasMany = [sessions:Session]
	
    static constraints = {
		nom(blank:false)
		mdp(size: 2..15, blank: false)
    }
}
