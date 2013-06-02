class Submission < ActiveRecord::Base
  attr_accessible :image

  has_attached_file 
	  :image, 
	  :styles => { :thumb => "100x100", }, 
	  :convert_options => { :thumb => "-quality 75 -strip" },
end
