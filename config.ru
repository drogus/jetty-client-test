app = lambda {|env|
  puts 'request'
  puts env['rack.input'].read.length

  [200, {}, ["a"]] }
run app
