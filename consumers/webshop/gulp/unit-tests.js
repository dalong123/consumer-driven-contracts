'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');
var spawn = require('child_process').spawn;

var karma = require('karma');

function startPactMockService(done) {
  console.log('Starting pact-mock-service');

  var args = ['exec', 'pact-mock-service', 'start'];
  args = args.concat(['-p', conf.pact.mockService.port]);
  args = args.concat(['-l', conf.pact.mockService.log]);
  args = args.concat(['-d', conf.pact.mockService.pactDir]);
  args = args.concat(['-o']);

  spawn('bundle', args, {stdio: 'inherit'})
    .on('close', function(code) {
      if (code === 0) {
        return done();
      }

      done(new Error('Could not start pact-mock-service, code=' + code));
    })
    .on('error', done);
}

function stopPactMockService(done) {
  console.log('stopping pact-mock-service');

  var args = ['exec', 'pact-mock-service', 'stop'];
  args = args.concat(['-p', conf.pact.mockService.port]);

  spawn('bundle', args, {stdio: 'inherit'})
    .on('close', function(code) {
      if (code === 0) {
        return done();
      }

      done(new Error('Could not stop pact-mock-service, code=' + code));
    })
    .on('error', done);
}

function runTests (singleRun, done) {
  startPactMockService(function(err) {
    if (err) {
      return done(err);
    }
    karma.server.start({
      configFile: path.join(__dirname, '../karma.conf.js'),
      singleRun: singleRun,
      autoWatch: !singleRun
    }, function(code) {
      if (code === 0) {
        return stopPactMockService(done);
      }

      stopPactMockService(function() {
        done(new Error('Error running tests: ' + code));
      });
    });
  });
}

gulp.task('test', ['scripts'], function(done) {
  runTests(true, done);
});

gulp.task('test:auto', ['watch'], function(done) {
  runTests(false, done);
});
