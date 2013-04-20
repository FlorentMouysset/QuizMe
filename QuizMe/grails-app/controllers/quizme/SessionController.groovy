package quizme

import org.hibernate.proxy.map.MapProxyFactory;
import org.springframework.dao.DataIntegrityViolationException

import questions.Question
import quizme.Room

class SessionController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]



	def createQuestion(){
		//		session["newquestion"] = null
		println "create question"
		params.each{
			println "##" + it
		}
		redirect( controller: "question", action: "create" )
	}

	def create() {
		def listQ = session["newquestion"]
		def it = listQ.iterator()
		def listNomQ = []
		while(it.hasNext()){
			listNomQ = listNomQ + Question.get(it.next())
			//listNomQ = listNomQ + q.getEnonce()
		}
		session["session.origin"] = "create"
		[sessionInstance: new Session(params), listQuestion : listNomQ ]
	}

	def save() {
		println "save session"
		/*	params.each{
		 println "##" + it
		 }*/
		def sessionInstance = new Session(nom:params["sessionNewName"],etat: SessionEtat.CREATION )
		if (!sessionInstance.save(flush: true)) {
			def listQ = session["newquestion"]
			def it = listQ.iterator()
			def listNomQ = []
			while(it.hasNext()){
				listNomQ = listNomQ + Question.get(it.next())
				//listNomQ = listNomQ + q.getEnonce()
			}

			render(view: "create", model: [sessionInstance: sessionInstance, listQuestion : listNomQ])
			return
		}
		sessionInstance.addQuestions(session["newquestion"])//ajout des questions à la session domaine
		session["newquestion"] = null //nettoyage session
		println "VERIF roomid " + session["room.id"]
		//println "V 2 "+   Room.findById(session["room.id"])
		def room = Room.findById(session["room.id"])
		//println "VERIF room " + room

		room.addSession(sessionInstance.id.toString())
		flash.message = message(code: 'default.created.message', args: [message(code: 'session.label', default: 'Session'), sessionInstance.id])
		redirect( controller: "room", action: "inroom" )
	}


	def enter(){
		println "session id => " + params["id"]
		session["sessionDomaine.id"] = params["id"]
		def sessionInstance = Session.findById(params["id"])

	//	println "*/* " + sessionInstance.aParticipe(session["user.id"].toString())
	//	println "*-*" + sessionInstance.mapResultats.containsKey(session["user.id"].toString())
	//	sessionInstance.mapResultats[session["user.id"].toString()]=null
		if( sessionInstance.aParticipe(session["user.id"].toString()) ){
			redirect( controller: "room", action: "inroom" )
		}
		
		if(sessionInstance.getEtat() != SessionEtat.ELABORATION ){
			render(view: "insession", model: [sessionInstance: sessionInstance])
		}else{
			render(view: "elaboration", model: [sessionInstance: sessionInstance])
		}
	}

	def finelaboration(){
		println "fin elaboration session = " + session["sessionDomaine.id"]
		params.each{
			println "##" + it
		}
		def sessionInstance = Session.findById(session["sessionDomaine.id"].toString())
		def map = params
		map.remove("_action_finelaboration")
		map.remove("action")
		map.remove("controller")
		map.each{
			println "??" + it
		}
		println "** " + sessionInstance
		sessionInstance.elaborationAjoutProposition(map)
		
		redirect( controller: "room", action: "inroom" )
	}


	def validation(){
		println "validation session"
		params.each{
			println "##" + it
		}

		def sessionInstance = Session.findById(session["sessionDomaine.id"].toString())		
		sessionInstance.ajoutParticipation(session["user.id"].toString(), params)	
		sessionInstance.mapResultats.each{
			println "#key " + it.key
			println "#value " + it.value
		}
		println "{{" + sessionInstance.mapResultats.containsKey(session["user.id"].toString())
		
		redirect( controller: "room", action: "inroom" )
	}

	def statechange(){
		println "state cahnge session"
		params.each{
			println "##" + it
		}
		def sessionDomaine = Session.findById(params["id"])
		println "**" + sessionDomaine
		sessionDomaine.evolutionEtat()
		redirect( controller: "room", action: "inroom" )
	}

	def stateelaboration(){
		def sessionDomaine = Session.findById(params["id"])
		println "**" + sessionDomaine
		sessionDomaine.evolutionElaboration()
		redirect( controller: "room", action: "inroom" )
	}

	/*
	 def show(Long id) {
	 def sessionInstance = Session.get(id)
	 if (!sessionInstance) {
	 flash.message = message(code: 'default.not.found.message', args: [message(code: 'session.label', default: 'Session'), id])
	 redirect(action: "list")
	 return
	 }
	 [sessionInstance: sessionInstance]
	 }*/

	def edit(Long id) {
		def sessionInstance = Session.get(id)
		if (!sessionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'session.label', default: 'Session'), id])
			redirect( controller: "room", action: "inroom" )
			return
		}


		def listQ = session["newquestion"]
		def it = listQ.iterator()
		def listNomQ = []
		while(it.hasNext()){
			listNomQ = listNomQ + Question.get(it.next())
			//listNomQ = listNomQ + q.getEnonce()
		}
		it = sessionInstance.questions.iterator()
		while(it.hasNext()){
			listNomQ = listNomQ +it.next()
			//listNomQ = listNomQ + q.getEnonce()
		}
		session["session.origin"] = "edit"
		session["session.id"] = id
		//		session["newquestion"] = null //nettoyage session
		[sessionInstance: sessionInstance, listQuestion : listNomQ ]
	}


	def update(Long id, Long version) {
		def sessionInstance = Session.get(id)
		println "--* ici"
		if (!sessionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'session.label', default: 'Session'), id])
			redirect( controller: "room", action: "inroom" )
			return
		}

		if (version != null) {
			if (sessionInstance.version > version) {
				sessionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'session.label', default: 'Session')] as Object[],
						"Another user has updated this Session while you were editing")
				render(view: "edit", model: [sessionInstance: sessionInstance])
				return
			}
		}

		params.each{
			println "##" + it
		}
		sessionInstance.properties = params
		sessionInstance.setNom(params["sessionNewName"])
		sessionInstance.addQuestions(session["newquestion"])//ajout des questions à la session domaine
		session["newquestion"] = null //nettoyage session

		if (!sessionInstance.save(flush: true)) {
			def listQ = session["newquestion"]
			def it = listQ.iterator()
			def listNomQ = []
			while(it.hasNext()){
				listNomQ = listNomQ + Question.get(it.next())
				//listNomQ = listNomQ + q.getEnonce()
			}
			it = sessionInstance.questions.iterator()
			while(it.hasNext()){
				listNomQ = listNomQ +it.next()
				//listNomQ = listNomQ + q.getEnonce()
			}
			render(view: "edit", model: [sessionInstance: sessionInstance, listQuestion : listNomQ])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'session.label', default: 'Session'), sessionInstance.id])
		redirect( controller: "room", action: "inroom" )
	}

	def delete(Long id) {
		println "DELETE > " + id
		def sessionInstance = Session.get(id)
		if (!sessionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'session.label', default: 'Session'), id])
			redirect( controller: "room", action: "inroom" )
			return
		}

		try {
			sessionInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'session.label', default: 'Session'), id])
			redirect( controller: "room", action: "inroom" )
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'session.label', default: 'Session'), id])
			redirect( controller: "room", action: "inroom" )
		}
	}
}
