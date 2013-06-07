class AddGleamToSubmission < ActiveRecord::Migration
  def change
  	add_column :submissions, :gleam, :integer
  end
end
