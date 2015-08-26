# web shop

This is just a simple example of how to use CDC from an Angular JS front-end
project.

The relevant code is in `src/app/components/cart/cart.service.spec.js`.

After cloning: like most Angular projects, run `npm install` and
`bower install`. The `npm install` command also runs `bundler install` to get
the required Ruby code.

To test: `npm test` (this starts and stops the Ruby `pact-mock-service`,
alternatively start it up yourself somewhere and run `gulp test`). The pact
is generated in `tmp/pacts`.

You can upload this to your Pact Broker, for now by hand. Using the excellent
[httpie](https://github.com/jkbrzt/httpie):

```sh
http -v PUT http://192.168.99.100:8085/pacts/provider/cart/consumer/webshop/version/1.0.0 < tmp/pacts/webshop-cart.json
```

Or, if you use curl:

```sh
curl -H "Content-Type: application/json" -v -X PUT -d @tmp/pacts/webshop-cart.json http://192.168.99.100:8085/pacts/provider/cart/consumer/webshop/version/1.0.0
```
