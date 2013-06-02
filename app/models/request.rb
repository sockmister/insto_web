class Request < ActiveRecord::Base
  attr_accessible :location

  belongs_to :user
  # belongs_to :location
end
