var Response = require("ringo/webapp/response").Response;
var Request = require("ringo/webapp/request").Request;
var auth = require("/auth");

exports.app = function(req) {
    var request = new Request(req);
    var details = auth.getDetails(request);
	
    // Empty content
    var content = "{}";
    
	if(request.isPost){
        // User posted something, use it
		var content = JSON.stringify(request.postParams);
		print("Post Content is : " + content);
	}
    
	var response = Response.skin(module.resolve("templates/vibi.html"), {status: details.status || 404, content: content});
	return response;
};
