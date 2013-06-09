class Submission < ActiveRecord::Base
  attr_accessible :image, :location_id, :user_id

  has_attached_file :image, 
  	:styles => { :thumb => "100x100", }, 
  	:convert_options => { :thumb => "-quality 75 -strip" }

  def image_url
  	image.url(:original)
  end
end
