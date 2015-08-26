/* globals console, Pact, XMLHttpRequest */
(function() {
  'use strict';

  var PACT_MOCK_SERVICE_PORT = 1234;

  describe('A CartService', function() {
    beforeEach(module('webapp'), function($provide) {
      // Output messages
      $provide.value('$log', console);
    });

    var cartClient, cartProvider, $httpBackend;

    beforeEach(function() {
      inject(function(CartService, _$httpBackend_) {
        cartClient = CartService;
        $httpBackend = _$httpBackend_;
        cartProvider = Pact.mockService({
          consumer: 'webshop',
          provider: 'cart',
          port: PACT_MOCK_SERVICE_PORT,
          done: function(error) {
            expect(error).toBe(null);
          }
        });

        $httpBackend.whenGET(/api/).respond(function(method, url, data, headers) {
          // pass through to Pact server
          var req = new XMLHttpRequest();
          var pactUrl = 'http://localhost:' + PACT_MOCK_SERVICE_PORT + url;
          console.debug('Rewrote ' + url + ' to ' + pactUrl);
          req.open(method, pactUrl, false);
          if (headers) {
            for (var name in headers) {
              if (headers.hasOwnProperty(name)) {
                req.setRequestHeader(name, headers[name]);
              }
            }
          }
          req.send(data);
          console.debug('Done request to ' + pactUrl + ', result: ' + req.status + ', data: ' + req.response + ', headers: ' + req.getAllResponseHeaders());
          return [req.status, req.response];
        });
      });
    });

    it('should return an empty cart if no user ID was specified', function(done) {
      cartProvider
        .uponReceiving('a request without user ID')
        .withRequest('get', '/api/cart')
        .willRespondWith(200, {}, {
          items: []
        });

      cartProvider.run(done, function(runComplete) {
        cartClient.query().then(function(result) {
          expect(result.items).toBeDefined();
          if (result.items) {
            expect(result.items.length).toBe(0);
          }
          runComplete();
        }, runComplete);

        $httpBackend.flush();
      });
    });
  });
})();
