# JavaScript CDC

## Steps taken

Followed [this manual](https://github.com/DiUS/pact-consumer-js-dsl#installing-pact-mock-service).
The list below is for people who are interested how we got here.

1.  `sudo gem install bundler`
2.  `bundle install`
3.  `mkdir frontend && cd $_`
4.  Create a template Angular front-end
    *   Because we are lazy, we just use Yeoman with
        [generator-gulp-angular](https://github.com/Swiip/generator-gulp-angular)
    *   `npm install -g yo gulp bower`
    *   `npm install -g generator-gulp-angular`
    *   `yo gulp-angular`, with some reasonable answers (it does not really
        matter here)
5.  `bower install pact-consumer-js-dsl --save-dev`
6.  Add the following to `karma.conf.js`:
        
        browsers: ['PhantomJS_without_security'],
        customLaunchers: {
          PhantomJS_without_security: {
            base: 'PhantomJS',
            flags: ['--web-security=false']
          }
        },

    And comment out the previous `browsers` element.
7.  Add a service and spec, see `src/app/components/report`
8.  Run the Ruby mock service:

        bundle exec pact-mock-service -p 1234 -l log/pact.logs --pact-dir tmp/pacts -o

9.  Run the tests: `gulp test`
10. The pact file is generated in
    `tmp/pacts/frontend-public_report_service.json`
11. Start the broker (`../broker`): `docker-compose up`
12. Upload the pact file:

        curl -H "Content-Type: application/json" -v -X PUT -d @tmp/pacts/frontend-public_report_service.json http://$(boot2docker ip):8080/pacts/provider/Public%20Report%20Service/consumer/frontend/version/1.0.0

13. See the result in the broker: `http://$(boot2docker ip):8080`

## TODO

*   The URL in the JavaScript service is hard coded, needs to be dynamic
*   The JavaScript service uses JQuery, as the Angular services (`$resource`
    and `$http`) did not execute the remote call when running from Karma.
*   Add the upload of the pact file to Gulp
