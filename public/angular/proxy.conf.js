const PROXY_CONFIG = {
  "**": {
    "target": "http://localhost:9000",
    "secure": false,
    "bypass": function (req) {
      console.log("Hi.");
      if (req && req.headers && req.headers.accept && req.headers.accept.indexOf("html") !== -1) {
        console.log("Skipping proxy for browser request.");
        return "angular/src/index.html";
      }
    }
  }
};

module.exports = PROXY_CONFIG;
