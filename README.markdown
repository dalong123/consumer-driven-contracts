# Consumer-driven-contracts

## Building

### shared infra (sort of)

The cart service provider uses a Postgresql database, and all Maven builds use
a Pact broker (which uses another Postgresql db). To run the shared infra:
```sh
cd infra
docker-compose up
```

Of course in real life we would not put the cart service's DB in a shared
place, but we have to stop somewhere in this example.

### pactbroker-maven-plugin

All Maven projects use the
[pactbroker-maven-plugin](https://github.com/warmuuh/pactbroker-maven-plugin),
but unfortunately with Maven >= 2.2 we cannot use commit hashes for
non-released code (`[ERROR] Invalid plugin descriptor for
com.github.warmuuh:pactbroker-maven-plugin:46924e29d3
(/Users/adriaan/.m2/repository/com/github/warmuuh/pactbroker-maven-plugin/46924e29d3/pactbroker-maven-plugin-46924e29d3.jar),
Plugin's descriptor contains the wrong version: 0.0.5-SNAPSHOT -> [Help 1]`).

So while there is no release with pact-broker support, do the following:

    git submodule update --init
    cd lib/pactbroker-maven-plugin
    mvn install

All Maven projects will usually require you to specify the broker URL:
`-Dpact-broker.url=http://192.168.99.100:8085` (for Linux users the default
value `http://localhost:8085` should work). The IP address is the address your
docker machine VM runs at (find out using `docker-machine ip <machine_name>`,
or `boot2docker ip` if you still use boot2docker).

To build a consumer and upload its pact file(s), run something like this:

```sh
mvn -Dpact-broker.url=http://192.168.99.100:8085 pactbroker:upload-pacts
```

A provider will automatically download all relevant pact files from the broker,
as part of the Maven `generate-resources `lifecycle phase, so most commands
require the pact-broker url:

```sh
mvn -Dpact-broker.url=http://192.168.99.100:8085 clean package
```
