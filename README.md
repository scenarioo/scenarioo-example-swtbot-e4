# scenarioo-example-swtbot-e4

An example of using scenarioo together with SWTBot to test and document a sample eclipse RCP e4 application

The UI tests and the Scenarioo integration can be found in the folder `plugins/org.scenarioo.example.e4.test/src/org/scenarioo/example/e4`.
# Build and run
Execute the shell script: start-tycho-build.sh
This command will build the full eclipse rcp product and execute the UI SwtBot tests.
Limitation: The SWTBot tests run correctly on a linux machine. We know that Windows does not work. Mac OS might work.
