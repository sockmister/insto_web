class Location < ActiveRecord::Base
  attr_accessible :faculty, :name

  has_many :requests
end
