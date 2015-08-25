FROM ruby:2.2

ADD app /opt/pact_broker

WORKDIR /opt/pact_broker

RUN bundle

EXPOSE 8080

CMD bundle exec rackup -o 0.0.0.0 -p 8080
