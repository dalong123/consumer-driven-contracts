(function() {
  'use strict';

  describe('client', function() {
    var client, reportProvider;

    beforeEach(module('frontend', function($provide) {
      // Output messages
      $provide.value('$log', console);
    }));

    beforeEach(function() {
      module('frontend');

      inject(function(Report) {
        client = Report;
        reportProvider = Pact.mockService({
          consumer: 'frontend',
          provider: 'Public Report Service',
          port: 1234,
          done: function(error) {
            expect(error).toBe(null);
          }
        });
      });
    });

    it('should return a report', function(done) {
      reportProvider
        .given('two scans with scores 3.2 and 1.7')
        .uponReceiving('a request for a report for all data')
        .withRequest('get', '/api/public/report', {
          'Accept': 'application/json'
        })
        .willRespondWith(200, {
          'Content-Type': 'application/json'
        }, {
          scans: [
            {
              score: 3.2
            }, {
              score: 1.7
            }
          ]
        });

      reportProvider.run(done, function(runComplete) {
        console.log('about to query', client);

        client.query(function(response) {
          console.log('in callback', JSON.stringify(response));
          var scans = response.scans;
          expect(scans.length).toEqual(2);
          expect(scans[0].score).toEqual(3.2);
          expect(scans[1].score).toEqual(1.7);
          runComplete();
        });
      });
    }, 3600000);
  });
})();
