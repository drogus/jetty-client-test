task :kirk_path do
  raise "Please provide a KIRK_PATH" unless ENV["KIRK_PATH"]
end

desc "Compile and run request for http client"
task :request => :kirk_path do

  jars = Dir[File.join(ENV["KIRK_PATH"], "lib/kirk/jetty/*.jar")] + ["./src/main/java"]
  jars = jars.join(":")

  `javac -cp #{jars} ./src/main/java/com/strobecorp/MyHttpClient.java`
  `java -cp #{jars} com/strobecorp/MyHttpClient`
end

desc "Run the test server"
task :server => :kirk_path do
  kirk = File.join(ENV["KIRK_PATH"], 'bin/kirk')
  `bundle exec #{kirk} -c Kirkfile`
end
