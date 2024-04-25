# eu-vat-rates-contract-tests-qa

eu-vat-rates Contract API tests.

## Running the tests

QA:

These tests were developed to run against the QA environment. 

They are currently ran as an ad-hoc job called eu-vat-rates-contract-tests-qa on jenkins but we may introduce a 
scheduled job at a later date. The jenkins job is configured via the build-jobs repo.

This test checks that the EC Vat Rates API is returning XML in the expected format. 

If this needs to be tested locally, please use the following steps:

Execute the `run-tests.sh` script:

`./run_tests.sh <environment>`

The tests default to the `local` environment.  For a complete list of supported param values, see:
- `src/test/resources/application.conf` for **environment**
```

## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
