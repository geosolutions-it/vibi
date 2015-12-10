exports.config = function(urls, middleware) {
	urls[0] = [(/^\/(index(.html)?)?/), require("./index").app];
    // Use unshift() to look for the custom application files before the default MapStore ones
	middleware.unshift(require("ringo/middleware/static").middleware({base: module.resolve("static")}));
};