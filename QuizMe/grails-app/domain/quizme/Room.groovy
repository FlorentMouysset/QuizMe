package quizme

import user.Etudiant
import user.Professeur

class Room {
	
	String nom
	String mdp
	Professeur admin
	//static hasMany = [sessions:Session, etudiants:Etudiant]
	static hasMany = [ etudiants:Etudiant, sessions:Session]
	
		
    static constraints = {
		nom(blank:false)
		mdp(size: 2..15, blank: false)
    }
	
	def estParticipant(def idDemande){
		def participe = false
		println "estPartici " + idDemande
		//def f = participants.findById(idDemande)
	//	def f = hasManyParticipants.findById(idDemande)
	//	hasManyParticipants.each{
	//		println "##" + it
	//	}
		
		def f = false
		println ">" + f
		participe
	}
	
	
	def addParticipant(def etudiant){
		println "add particiant " + etudiant.getId()
		println "##" + Etudiant.findById(etudiant.getId())
		addToSessions(new Session())
		addToEtudiants(Etudiant.findById(etudiant.getId()))
	}
}
