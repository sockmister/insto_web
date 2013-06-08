class Request < ActiveRecord::Base
  attr_accessible :location, :user_id, :location_id

  belongs_to :user
  belongs_to :location
end
