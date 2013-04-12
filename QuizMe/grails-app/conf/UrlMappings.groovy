class UrlMappings {

	static mappings = {
		

		
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

 		println "ici urlmap" 
		

		 
//		"/authentification/save" {
//			println "ici urlmap2"
//			//controller = "AuthentificationController"
//			controller = "AuthentificationController"
//			action = "index"
//		}
//		
	//	 "/index"(view:"authentification/list")
		"/"(view:"/index")
		"500"(view:'/error')
		
		
	}
}
