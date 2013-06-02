class User < ActiveRecord::Base
  attr_accessible :points, :user

  has_many :requests
	# has_many :submissions
end
