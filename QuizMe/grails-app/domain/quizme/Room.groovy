package quizme

import user.Etudiant
import user.Professeur

class Room {
	
	String nom
	String mdp
	Professeur admin
	static hasMany = [sessions:Session]
	static hasManyParticipants = [participants:Etudiant]
	
		
    static constraints = {
		nom(blank:false)
		mdp(size: 2..15, blank: false)
    }
	
	def estParticipant(def idDemande){
		def participe = false
		println "estPartici " + idDemande
		//def f = participants.findById(idDemande)
	//	def f = hasManyParticipants.findById(idDemande)
		hasManyParticipants.each{
			println "##" + it
		}
		
		def f = false
		println ">" + f
		participe
	}
	
	
	def addParticipant(def idEtudiant){
		println "add particiant " + idEtudiant
		
	}
}
