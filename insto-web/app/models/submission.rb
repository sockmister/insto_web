require 'socket'

class Submission < ActiveRecord::Base
	host = "penbites.info.tm"

  attr_accessible :image, :location_id, :user_id

  has_attached_file :image, 
  	:styles => { :thumb => "100x100", }, 
  	:convert_options => { :thumb => "-quality 75 -strip" },
  	:url => "#{host}/images/:id/:style_:basename.:extension",
   	:path => ":rails_root/public/images/:id/:style_:basename.:extension"

  def image_url
  	image.url(:original)
  end

  def for_fun
  	image.url(:thumbnail)
  end
end
