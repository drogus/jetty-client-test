require 'fileutils'

task :kirk_path do
  raise "Please provide a KIRK_PATH" unless ENV["KIRK_PATH"]
end

task :generate_data do
  return if File.exist?("tmp/data") && ENV['REQUEST_TASK']
  FileUtils.mkdir_p("tmp")
  File.open("tmp/data", "w") do |f|
    f.puts 'a'*1_000_000
    f.puts 'b'*500_000
    f.puts 'c'*250_000
    f.puts 'd' * 125_000
    f.puts 'e'*50_000
    f.puts 'f' * 25_000
    f.puts 'g' * 25_000
    f.puts 'g' * 25_000
    f.puts 'h'*1000
    f.puts 'i'*200
  end
end

desc "Compile and run request for http client"
task :request => [:kirk_path] do
  ENV['REQUEST_TASK'] = true
  Rake::Task[:generate_data].invoke

  jars = Dir[File.join(ENV["KIRK_PATH"], "lib/kirk/jetty/*.jar")] + ["./src/main/java"]
  jars = jars.join(":")

  `javac -cp #{jars} ./src/main/java/com/strobecorp/MyHttpClient.java`
  exec "java -cp #{jars} com/strobecorp/MyHttpClient"
end

desc "Run the test server"
task :server => :kirk_path do
  kirk = File.join(ENV["KIRK_PATH"], 'bin/kirk')
  exec "bundle exec #{kirk} -c Kirkfile"
end
