app = lambda {|env|
  p [:BODY_LENGTH, env['rack.input'].read.length]

  [200, {}, ["ZOMG O_o"]] }
run app
